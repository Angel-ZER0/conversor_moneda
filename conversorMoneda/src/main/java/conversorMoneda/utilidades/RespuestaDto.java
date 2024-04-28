package conversorMonedaJava.utilidades;
/*Record que se usa como una objeto de transferecia de datos para adquirir los valores de las propiedades deseadas de
la respuesta del json revibido en la solicitud http*/
public record RespuestaDto(String baseCode, String targetCode, String conversionRate) {
	
}
