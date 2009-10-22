package king.lib.script.model;

/**
 * A compiled script.
 *
 * @author  noblemaster
 * @since  October 21, 2009
 */
public interface Compile {

  /**
   * Executes the compiled script.
   * 
   * @param context  The context.
   * @param object  The input.
   * @return  The output.
   */
  Object execute(Context context, Object object);
}
