package conversorMoneda.utilidades;
public class MetodoPush {
    //se crea un arrego de tipo string con 10 espacios
	private String[] arreglo = new String[10];
	//un dato de tipo integer que representará al índice de arreglo creado
    private int index = 0;
    //
    /*método que se encargará de llenar el arreglo y que cuando el mismo esté completo eliminará el que esté en el
    índice 0, recorrerá los otros resultados y colocará el nuevo en la últim pocisión del mismo*/
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
    //Método que mostrará los índices del arreglo en la consola
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
