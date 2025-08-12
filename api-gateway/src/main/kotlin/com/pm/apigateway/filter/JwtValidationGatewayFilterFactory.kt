import org.springframework.beans.factory.annotation.Value
import org.springframework.cloud.gateway.filter.GatewayFilter
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient

@Component
class JwtValidationGatewayFilterFactory(
    webClientBuilder: WebClient.Builder,
    @Value("\${auth.service.url}") authServiceUrl: String
) : AbstractGatewayFilterFactory<Any>(Any::class.java) {

    private val webClient: WebClient = webClientBuilder
        .baseUrl(authServiceUrl)
        .build()

    override fun apply(config: Any): GatewayFilter {
        return GatewayFilter { exchange, chain ->
            val token = exchange.request.headers.getFirst(HttpHeaders.AUTHORIZATION)

            if (token == null || !token.startsWith("Bearer ")) {
                exchange.response.statusCode = HttpStatus.UNAUTHORIZED
                return@GatewayFilter exchange.response.setComplete()
            }

            webClient.get()
                .uri("/validate")
                .header(HttpHeaders.AUTHORIZATION, token)
                .retrieve()
                .toBodilessEntity()
                .then(chain.filter(exchange))
        }
    }
}
