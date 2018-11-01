package com.yz.springboot.util.parser;

/**
 * @author huiRan
 * @date 2018/9/12
 */
public final class ParserFactory {

    private ParserFactory() {
        throw new UnsupportedOperationException("u can't init me...");
    }

    public static IParser getParser(ParserEnum parserEnum) {
        checkNotNull(parserEnum, "parserEnum is null");
        if (parserEnum == ParserEnum.EXCEL) {
            return new ExcelParserImpl();
        }
        return null;
    }

    private static void checkNotNull(Object o, String msg) {
        if (o == null) {
            throw new NullPointerException("ParserFactory...." + msg);
        }
    }

    public enum ParserEnum {
        /**
         * excel
         */
        EXCEL
    }
}
