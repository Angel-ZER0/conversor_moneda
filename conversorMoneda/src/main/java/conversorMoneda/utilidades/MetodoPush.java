package conversorMoneda.utilidades;
public class MetodoPush {
	private String[] arreglo = new String[10];
    private int index = 0;

    public void push(String consulta) {
        if (index < 10) {
        	arreglo[index] = consulta;
            index++;
        } else {
            for (byte i = 0; i < 9; i++) {
            	arreglo[i] = arreglo[i + 1];
            }
            arreglo[9] = consulta;
        }
    }
    public void imprimirArreglo() {
    	for (byte i = 0; i < arreglo.length; i++) {
    		if (arreglo[i] != null) {
    			System.out.println(arreglo[i]);
    		} else {
    			System.out.println("Esperando consulta para mostrar");
    		}
    	}
    }
}
