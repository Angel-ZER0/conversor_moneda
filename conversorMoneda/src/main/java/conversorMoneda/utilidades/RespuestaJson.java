package conversorMoneda.utilidades;
import com.google.gson.annotations.SerializedName;
public class RespuestaJson {
	/*Uso de la anotación @SerializedName para colocar en el String monedaBase el valor de la propiedad del json
	que tenga la propiedad "base_code"*/
	@SerializedName("base_code")
	private String monedaBase;
	/*Uso de la anotación @SerializedName para colocar en el String monedaConvertida valor de la propiedad del json
	que tenga la propiedad "target_code"*/
	@SerializedName("target_code")
	private String monedaConvertida;
	/*Uso de la anotación @SerializedName para colocar en el String valorMoneda valor de la propiedad del json
	que tenga la propiedad "conversion_rate"*/
	@SerializedName("conversion_rate")
	private double valorMoneda;
	//Constructor sin atributos
	public RespuestaJson(){
		
	}
	//Constructor con todos los atributos
	public RespuestaJson(String monedaBase, String monedaConvertida, double valorMoneda) {
		this.monedaBase = monedaBase;
		this.monedaConvertida = monedaConvertida;
		this.valorMoneda = valorMoneda;
	}
	/*Método que recibe un dato de tipo double, el cual realiza una opereación usando el dato que recibe de la clase que, que ejecuta el Método main, más el dato de dato de tipo double  recibe de la información del json y al final todo se
	pasa a una cadena que contiene el las siglas de las monedas usadas y la cantidad de la conversión realizada*/
	public String transformarCantidad(double cantidadAConvertir) {
		double conversionFinal = this.valorMoneda * cantidadAConvertir;
		String cadenaFinal = cantidadAConvertir + " " + this.monedaBase + " equivalen a " + String.format("%.4f", conversionFinal) + " " + this.monedaConvertida;
		return cadenaFinal;
	}
	//Métodos get y set
	public String getMonedaBase() {
		return monedaBase;
	}
	public void setMonedaBase(String monedaBase) {
		this.monedaBase = monedaBase;
	}
	public String getMonedaConvertida() {
		return monedaConvertida;
	}
	public void setMonedaConvertida(String monedaConvertida) {
		this.monedaConvertida = monedaConvertida;
	}
	public double getValorMoneda() {
		return valorMoneda;
	}
	public void setValorMoneda(double valorMoneda) {
		this.valorMoneda = valorMoneda;
	}
	//Método to String
	@Override
	public String toString() {
		return "RespuestaJson [monedaBase=" + monedaBase + ", monedaConvertida=" + monedaConvertida + ", valorMoneda="
				+ valorMoneda + "]";
	}
}

