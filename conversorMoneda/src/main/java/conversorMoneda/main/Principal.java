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
		//Objeto scanner para permitir interacción y registro con el usuario
		Scanner entrada = new Scanner(System.in);
		//dato booleano que sirve para verificar si las respuestas de las variables son válidas
		boolean verificarRespuesta;
		//Objeto LocalTime que servirá para imprir la hora de la conversión de la moneda
		LocalTime horaLocal = LocalTime.now();
		//Objeto DateTimeFormatter que sirve para dar formato a la hora
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("HH:mm:ss");
		//Arreglo que contiene las siglas de las monedas que se pueden consultar con este programa
		String [] arregloMonedasValidas = { "ARS", "AUD", "BOB", "BRL", "CAD", "CHF", "CLP", "CNY", "COP", "CRC", "CUP",
				"DKK", "DOP", "EUR", "GBP", "GTQ", "HKD", "HNL", "INR", "JPY", "KYD", "MXN", "NIO", "NZD", "PAB", "PEN",
				"PYG", "RUB", "SEK", "SGD", "TTD", "USD", "UYU", "VES" };
		//Instancia con la clase MetodoPush que crea un arreglo y contine métodos para añadir datos al mismo y mostrarlos
		MetodoPush arregloConsultas = new MetodoPush();
		//variable que contiene el ménu de las monedas consultables
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
		//Cadena que se usa para conocer si el usuario quiere realizar otra conversión o salir del programa
		String seleccion;
		//Cadena donde se almacena la moneda de la que se quiere convertir
		String seleccionMoneda = "";
		//Cadena donde se almacena la moneda a la que se quiere tranformar
		String seleccionTransformar = "";
		//Cadena que almacena la cantidad  que se quiere convertir
		String cantidadAConvertir = "";

		System.out.println("Bienvenido al conversor de moneda");
		//ciclo do ... while que se encarga de seguir ejecutando el código hasta que el usuario escriba la cadena indicada para cerrarlo
		do {
			System.out.println("Escribe el número de la moneda del pais de la que deseas deseas convertir: ");
			//Impresión en consola del menú de las monedas
			System.out.println(menuMonedas);
			//uso del scanner para registrar la moneda seleccionada
			seleccionMoneda = entrada.nextLine();
			//ciclo while que implementa un booleano para verificar que la respuesta del usuario sea válida
			verificarRespuesta = seleccionMoneda.matches("[0-9]+");
			while (verificarRespuesta == false || Byte.parseByte(seleccionMoneda) <= 0
					|| Byte.parseByte(seleccionMoneda) > 34) {
				System.out.println("Opción inválida ingresada, elige un número válido: ");
				seleccionMoneda = entrada.nextLine();
				verificarRespuesta = seleccionMoneda.matches("[0-9]+");
			}

			System.out.println("Escribe el número de la moneda a la que deseas convertir: ");
			//uso del objeto scanner para registrar la seleccion de la moneada a la que se va a tranformar
			seleccionTransformar = entrada.nextLine();
			//ciclo while que implementa un booleano para verificar que la respuesta del usuario sea correcta
			verificarRespuesta = seleccionTransformar.matches("[0-9]+");
			while (verificarRespuesta == false || Byte.parseByte(seleccionTransformar) <= 0
					|| Byte.parseByte(seleccionTransformar) > 34) {
				System.out.println("Opción inválida ingresada, elige un número válido: ");
				seleccionTransformar = entrada.nextLine();
				verificarRespuesta = seleccionTransformar.matches("[0-9]+");
			}

			System.out.println("Ingresa la cantidad de " + arregloMonedasValidas[Integer.parseInt(seleccionMoneda) - 1]
					+ " que deseas tranformar a " + arregloMonedasValidas[Integer.parseInt(seleccionTransformar) - 1]);
			//uso del objeto scanner para registrar la respues del usuario
			cantidadAConvertir = entrada.nextLine();
			//ciclo while que implementa un booleano para verificar que la respuesta del usuario sea correcta
			verificarRespuesta = cantidadAConvertir.matches("[0-9.]+");
			while (verificarRespuesta == false) {
				System.out.println("Ingresa la cantidad de "
						+ arregloMonedasValidas[Integer.parseInt(seleccionMoneda) - 1] + " que deseas tranformar a "
						+ arregloMonedasValidas[Integer.parseInt(seleccionTransformar) - 1]);
				System.out.println("Ingresa solo números y un solo signo de punto de ser necesario: ");
				cantidadAConvertir = entrada.nextLine();
				verificarRespuesta = cantidadAConvertir.matches("[0-9.]+");
			}
			//Creacion de objeto HttpClient
			HttpClient cliente = HttpClient.newHttpClient();
			/*Creacion de objeto HttpRequest donde se coloca la uri y los parámetros de la misma, usando los datos de
			seleccionMoneda y seleccionTransformar, tranformados a datos byte, restándole uno para de esa forma poder
			ubicar la cadena dentro del arreglo arregloMonedasValidas que hace referencia a dichas monedas*/
			HttpRequest peticion = HttpRequest.newBuilder()
					.uri(URI.create("https://v6.exchangerate-api.com/v6/79293fd506db95927a7a3e14/pair/"
							+ arregloMonedasValidas[Byte.parseByte(seleccionMoneda) - 1] + "/"
							+ arregloMonedasValidas[Byte.parseByte(seleccionTransformar) - 1]))
					.build();
			//se envía la petición a la API y se guarda la respuesta en el objeto HttpResponse
			HttpResponse<String> respuesta = cliente.send(peticion, HttpResponse.BodyHandlers.ofString());
			//Se guarda la respuesta del cuerpo en una nueva variable de jsonACadena de tipo String
			String jsonACadena = respuesta.body();
			//Se utiliza la librería Gson para transformar el dato jsonACadena a cadena en un objeto de la clase RespuestaJson
			Gson jsonRecibido = new Gson();
			RespuestaJson conversion = jsonRecibido.fromJson(jsonACadena, RespuestaJson.class);
			/*Se usa el método transformarCantidad al que se le pasa el atributo cantidadAConvertir transformado a double
			para realizar la operación y regresar el resultado como una cadena y se imprime en la consola junto con la
			hora de la conversión*/
			System.out.println("La conversión actual es: "
					+ conversion.transformarCantidad(Double.parseDouble(cantidadAConvertir) + " hora de la conversión: " + horaLocal.format(formato));;
			//Se usa el método push de la clase MetodoPush para colocar el resultado de la operación dentro de un arreglo
			arregloConsultas.push(conversion.transformarCantidad(Double.parseDouble(cantidadAConvertir)));
			//Se usa el método imprimirArreglo para mostrar el contenido del arreglo en la consola
			arregloConsultas.imprimirArreglo();

			System.out.print("""
					¿Deseas realizar otra conversión?
					introducir
					Si: continuar
					No: cerrar el programa
					""");
			//Uso del objeto scanner para registrar el valor que tendrá asignada la variable seleccion
			seleccion = entrada.nextLine();
			//uso de ciclo while para para evitar salidas del programa no deseadas
			while (!seleccion.toLowerCase().equals("si") && !seleccion.toLowerCase().equals("no")) {
				System.out.println("Escribe una repuesta válida: [Si/No]");
				seleccion = entrada.nextLine();
			}
			//Reinicio de la hora que imprimirá en consola
			horaLocal = LocalTime.now();
		//Declaración de condición que finaliza el ciclo
		} while (seleccion.toLowerCase().equals("si"));
		entrada.close();
		System.out.println("Hasta la proxima.");

	}
}
