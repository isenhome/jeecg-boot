package org.jeecg.modules.dsp.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.text.DecimalFormat;


public class NumberFormat4 extends JsonSerializer<Number> {

    private DecimalFormat df = new DecimalFormat("0.0000");

    @Override
    public void serialize(Number number, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (number != null) {
            jsonGenerator.writeNumber(df.format(number.doubleValue()));
        }
    }
}
