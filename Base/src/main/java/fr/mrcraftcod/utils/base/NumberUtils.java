package fr.mrcraftcod.utils.base;

/**
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 09/03/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-03-09
 */
@SuppressWarnings({
		"unused",
		"WeakerAccess"
})
public class NumberUtils{
	/**
	 * Perform a modulo that works on negative numbers.
	 *
	 * @param number The number.
	 * @param base   The base of the modulo.
	 *
	 * @return The modulo.
	 */
	private static int mod(int number, int base){
		if(number >= 0){
			return number % base;
		}
		return base + number % base;
	}
}
