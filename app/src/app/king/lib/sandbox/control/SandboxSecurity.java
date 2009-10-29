package king.lib.sandbox.control;

import java.security.Permission;
import java.util.HashSet;
import java.util.Set;

/**
 * Executes threads in a sandboxed environment. 
 * <p>
 * Usage:
 * <code>
 *   // install the sandbox security manager (only active for sandboxed threads)
 *   SandboxSecurity.install();
 *   ...
 *   // execute dangerous code for current thread in sandbox
 *   SandboxSecurity.sandbox(Thread.currentThread());
 *   [potentially dangerous code]
 *   SandboxSecurity.unsandbox(Thread.currentThread());
 *   ...
 *   // restore previous security manager
 *   SandboxSecurity.uninstall();
 * </code>
 * 
 * @author noblemaster
 * @since  October 23, 2009
 */
public final class SandboxSecurity extends SecurityManager {
  
  /** The only instance of sandbox security. */
  private static SandboxSecurity instance = new SandboxSecurity();

  /** The underlying security manager. */
  private SecurityManager securityManager = null;    
  /** True for active. */
  private boolean installed;
  /** The sandboxed threads. */
  private Set sandboxedThreads = new HashSet();
      
  
  /**
   * Installs sandbox security.
   * 
   * @throws SandboxException  If already installed.
   */
  public static void install() throws SandboxException {
    synchronized (instance) {
      if (!isInstalled()) {
        // install it
        instance.securityManager = System.getSecurityManager();
        System.setSecurityManager(instance);
        instance.installed = true;
      }
      else {
        throw new SandboxException("error.SandboxSecurityAlreadyInstalled[i18n]: Sandbox security is already active.");
      }
    }
  }
  
  /**
   * Uninstalls sandbox security.
   * 
   * @throws SandboxException  If not installed or there were active threads still sandboxed.
   */
  public static void uninstall() throws SandboxException {
    synchronized (instance) {
      if (isInstalled()) {
        if (instance.sandboxedThreads.size() == 0) {
          // uninstall it
          instance.installed = false;
          System.setSecurityManager(instance.securityManager);
          instance.securityManager = null;
        }
        else {
          throw new SandboxException("error.SandboxThreadsActive[i18n]: Remove sandboxed threads first.");
        }
      }
      else {
        throw new SandboxException("error.SandboxSecurityNotInstalled[i18n]: Sandbox security was not installed.");
      }
    }
  }
  
  /**
   * Returns true if sandbox security is active.
   * 
   * @return  True for active.
   */
  public static boolean isInstalled() {
    return instance.installed;
  }

  /**
   * Adds the current thread to sandbox.
   */
  public static void sandbox() {
    instance.sandboxedThreads.add(Thread.currentThread());
  }
  
  /**
   * Removes the current from sandbox.
   */
  public static void unsandbox() {
    instance.sandboxedThreads.remove(Thread.currentThread());
  }

  /**
   * Adds the inputed thread to sandbox.
   * 
   * @param thread  The thread.
   */
  public static void sandbox(Thread thread) {
    instance.sandboxedThreads.add(thread);
  }
  
  /**
   * Removes the inputed thread from sandbox.
   * 
   * @param thread  The thread.
   */
  public static void unsandbox(Thread thread) {
    instance.sandboxedThreads.remove(thread);
  }
  
  /**
   * Checks the permissions and throws a SecurityException if permission is not given. Blocks
   * all the sandboxed threads.
   * 
   * @param permission  The permission to check for.
   */
  @Override
  public void checkPermission(Permission permission) {
    if (sandboxedThreads.contains(Thread.currentThread())) {
      throw new SecurityException("Permission not available for sandboxed thread: " + permission);
    }
    else {
      // pass it down to actual security manager as needed
      if (securityManager != null) {
        securityManager.checkPermission(permission);
      }
    }
  }
  
  /**
   * Checks the permissions and throws a SecurityException if permission is not given. Blocks
   * all the sandboxed threads.
   * 
   * @param permission  The permission to check for.
   * @param context  The context.
   */
  @Override
  public void checkPermission(Permission permission, Object context) {
    checkPermission(permission);
  }

  /**
   * Checks for thread access permission.
   * 
   * @param thread  The thread.
   */
  @Override
  public void checkAccess(Thread thread) {    
    // and check
    if (sandboxedThreads.contains(Thread.currentThread())) {
      super.checkAccess(thread);
      throw new SecurityException("Thread access not available for sandboxed thread.");
    }
    else {
      // pass it down to actual security manager as needed
      if (securityManager != null) {
        securityManager.checkAccess(thread);
      }
    }
  }

  /**
   * Checks for thread group access permission.
   * 
   * @param group  The thread.
   */
  @Override
  public void checkAccess(ThreadGroup group) {   
    // and check
    if (sandboxedThreads.contains(Thread.currentThread())) {
      super.checkAccess(group);
      throw new SecurityException("ThreadGroup access not available for sandboxed thread.");
    }
    else {
      // pass it down to actual security manager as needed
      if (securityManager != null) {
        securityManager.checkAccess(group);
      }
    }
  }
}

