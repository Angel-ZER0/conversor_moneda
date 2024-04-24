package conversorMoneda.main;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;
import com.google.gson.Gson;

import conversorMoneda.utilidades.MetodoPush;
import conversorMoneda.utilidades.RespuestaJson;

public class Principal {

	public static void main(String[] args) throws IOException, InterruptedException {
		Scanner entrada = new Scanner(System.in);
		boolean verificarRespuesta;
		String[] arregloMonedasValidas = { "ARS", "AUD", "BOB", "BRL", "CAD", "CHF", "CLP", "CNY", "COP", "CRC", "CUP",
				"DKK", "DOP", "EUR", "GBP", "GTQ", "HKD", "HNL", "INR", "JPY", "KYD", "MXN", "NIO", "NZD", "PAB", "PEN",
				"PYG", "RUB", "SEK", "SGD", "TTD", "USD", "UYU", "VES" };
		MetodoPush arregloConsultas = new MetodoPush();
		String menuMonedas = """

				1 ARS: Argentina - Peso                         2 AUD: Australia - Dólar
				3 BOB: Bolivia - Peso                               4 BRL: Brasil - Real
				5 CAD: Canadá - Dólar                              6 CHF: Suiza - Franco
				7 CLP: Chile - Peso                                  8 CNY: China - Yuan
				9 COP: Colombia - Peso                        10 CRC: Costa Rica - Colón
				11 CUP: Cuba - Peso                           12 DKK: Dinamarca - Corona
				13 DOP: República Dominicana - Peso         14 EUR: Unión Europea - Euro
				15 GBP: Reino Unido - Libra esterlina        16 GTQ: Guatemala - Quetzal
				17 HKD: Hong Kong - Dólar de Hong Kong        18 HNL: Honduras - Lempira
				19 INR: India - Rupia                                20 JPY: Japón - Yen
				21 KYD: Islas Caiman dollar                        22 MXN: México - Peso
				23 NIO: Nicaragua Córdoba                  24 NZD:1 Nueva Zelanda dollar
				25 PAB: Panama Balboa                                   26 PEN: Perú Sol
				27 PYG: Paraguay Guarani                   28 RUB: Federación Rusa Rublo
				29 SEK: Suecia Corona                             30 SGD: Singapur Dolar
				31 TTD: Trinidad y Tobago Dolar             32 USD: Estados Unidos Dolar
				33 UYU: Uruguay Peso                  34 VES: Venezuela Bolívar soberano
				""";
		String seleccion;
		String seleccionMoneda = "";
		String seleccionTransformar = "";
		String cantidadAConvertir = "";

		System.out.println("Bienvenido al conversor de moneda");
		do {
			System.out.println("Escribe el número de la moneda del pais de la que deseas deseas convertir: ");
			System.out.println(menuMonedas);
			seleccionMoneda = entrada.nextLine();
			verificarRespuesta = seleccionMoneda.matches("[0-9]+");
			while (verificarRespuesta == false || Byte.parseByte(seleccionMoneda) <= 0
					|| Byte.parseByte(seleccionMoneda) > 34) {
				System.out.println("Opción inválida ingresada, elige un número válido: ");
				seleccionMoneda = entrada.nextLine();
				verificarRespuesta = seleccionMoneda.matches("[0-9]+");
			}

			System.out.println("Escribe el número de la moneda a la que deseas convertir: ");
			seleccionTransformar = entrada.nextLine();
			verificarRespuesta = seleccionTransformar.matches("[0-9]+");
			while (verificarRespuesta == false || Byte.parseByte(seleccionTransformar) <= 0
					|| Byte.parseByte(seleccionTransformar) > 34) {
				System.out.println("Opción inválida ingresada, elige un número válido: ");
				seleccionTransformar = entrada.nextLine();
				verificarRespuesta = seleccionTransformar.matches("[0-9]+");
			}

			System.out.println("Ingresa la cantidad de " + arregloMonedasValidas[Integer.parseInt(seleccionMoneda) - 1]
					+ " que deseas tranformar a " + arregloMonedasValidas[Integer.parseInt(seleccionTransformar) - 1]);
			cantidadAConvertir = entrada.nextLine();
			verificarRespuesta = cantidadAConvertir.matches("[0-9.]+");
			while (verificarRespuesta == false) {
				System.out.println("Ingresa la cantidad de "
						+ arregloMonedasValidas[Integer.parseInt(seleccionMoneda) - 1] + " que deseas tranformar a "
						+ arregloMonedasValidas[Integer.parseInt(seleccionTransformar) - 1]);
				System.out.println("Ingresa solo números y un solo signo de punto de ser necesario: ");
				cantidadAConvertir = entrada.nextLine();
				verificarRespuesta = cantidadAConvertir.matches("[0-9.]+");
			}

			HttpClient cliente = HttpClient.newHttpClient();
			HttpRequest peticion = HttpRequest.newBuilder()
					.uri(URI.create("https://v6.exchangerate-api.com/v6/79293fd506db95927a7a3e14/pair/"
							+ arregloMonedasValidas[Byte.parseByte(seleccionMoneda) - 1] + "/"
							+ arregloMonedasValidas[Byte.parseByte(seleccionTransformar) - 1]))
					.build();
			HttpResponse<String> respuesta = cliente.send(peticion, HttpResponse.BodyHandlers.ofString());
			String jsonACadena = respuesta.body();
			Gson jsonRecibido = new Gson();
			RespuestaJson conversion = jsonRecibido.fromJson(jsonACadena, RespuestaJson.class);
			System.out.println("La conversión actual es: "
					+ conversion.transformarCantidad(Double.parseDouble(cantidadAConvertir)));
			arregloConsultas.push(conversion.transformarCantidad(Double.parseDouble(cantidadAConvertir)));
			arregloConsultas.imprimirArreglo();

			System.out.print("""
					¿Deseas realizar otra conversión?
					introducir
					Si: continuar
					No: cerrar el programa
					""");
			seleccion = entrada.nextLine();
			while (!seleccion.toLowerCase().equals("si") && !seleccion.toLowerCase().equals("no")) {
				System.out.println("Escribe una repuesta válida: [Si/No]");
				seleccion = entrada.nextLine();
			}
		} while (seleccion.toLowerCase().equals("si"));
		entrada.close();
		System.out.println("Hasta la proxima.");

	}
}
