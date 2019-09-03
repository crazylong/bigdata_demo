package com.coderlong.mybatisplus.util;

import java.lang.reflect.Type;
 
import org.bson.types.Decimal128;
 
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.util.TypeUtils;
 
public class DecimalDeserializer implements ObjectDeserializer {

	@SuppressWarnings("unchecked")
	@Override
	public Decimal128 deserialze(DefaultJSONParser parser, Type type, Object filedName) {
		Object parse = parser.parse();

		return new Decimal128(TypeUtils.castToBigDecimal(parse));
	}

	@Override
	public int getFastMatchToken() {
		// TODO Auto-generated method stub
		return 0;
	}
}