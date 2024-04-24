package conversorMoneda.utilidades;
import com.google.gson.annotations.SerializedName;
public class RespuestaJson {
	@SerializedName("base_code")
	private String monedaBase;
	@SerializedName("target_code")
	private String monedaConvertida;
	@SerializedName("conversion_rate")
	private double valorMoneda;
	public RespuestaJson(){
		
	}
	public RespuestaJson(String monedaBase, String monedaConvertida, double valorMoneda) {
		this.monedaBase = monedaBase;
		this.monedaConvertida = monedaConvertida;
		this.valorMoneda = valorMoneda;
	}
	public String transformarCantidad(double cantidadAConvertir) {
		double conversionFinal = this.valorMoneda * cantidadAConvertir;
		String cadenaFinal = cantidadAConvertir + " " + this.monedaBase + " equivalen a " + String.format("%.4f", conversionFinal) + " " + this.monedaConvertida;
		return cadenaFinal;
	}
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
	@Override
	public String toString() {
		return "RespuestaJson [monedaBase=" + monedaBase + ", monedaConvertida=" + monedaConvertida + ", valorMoneda="
				+ valorMoneda + "]";
	}
}

