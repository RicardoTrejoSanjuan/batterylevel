package uk.co.senab.photoview.log;

public final class LogManager {
  private static Logger logger = new LoggerDefault();
  
  public static Logger getLogger() {
    return logger;
  }
  
  public static void setLogger(Logger paramLogger) {
    logger = paramLogger;
  }
}


/* Location:              C:\Users\dell\Desktop\Ricardo\dex2jar-2.0\dex2jar-2.0\classes-dex2jar.jar\\uk\co\senab\photoview\log\LogManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */