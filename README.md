In static method:
    ``在静态方法中获取当前类名``
    String clazz = Thread.currentThread() .getStackTrace() [1].getClassName();
    ``在静态方法中获取当前方法名``
    String method = Thread.currentThread() .getStackTrace()[1].getMethodName();
 
In normal method:
    ``在普通方法中获取当前类名``
    String clazz = this.getClass().getSimpleName(); 
    ``在普通方法中获取当前方法名``
    String method = Thread.currentThread() .getStackTrace()[1].getMethodName(); 