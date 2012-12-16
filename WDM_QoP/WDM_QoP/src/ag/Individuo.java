package ag;

/**
 * Interface del Individuo que define las operaciones propias del individuo.
 * 
 * @author mrodas
 * 
 */
public interface Individuo {

	/**
	 * Funci�n para calcular el Fitness del Individuo.
	 * 
	 * @return fitness del Individuo
	 */
	public double evaluar();

}
