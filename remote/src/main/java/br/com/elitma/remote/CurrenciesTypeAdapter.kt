package br.com.elitma.remote

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter

class CurrenciesTypeAdapter : TypeAdapter<CurrenciesResponse>() {

    override fun write(out: JsonWriter?, value: CurrenciesResponse?) {
        out?.beginArray()
        value?.currencies?.forEach {
            out?.beginObject()
            out?.name("code")
            out?.value(it.code)
            out?.name("name")
            out?.value(it.name)
            out?.name("precision")
            out?.value(it.precision)
            out?.name("_type")
            out?.value(it.type)
            out?.endObject()
        }
        out?.endArray()
    }

    override fun read(reader: JsonReader): CurrenciesResponse {
        val currencies = mutableListOf<CurrencyResponse>()

        reader.beginObject()

        while (reader.hasNext()) {
            var token: JsonToken
            var fieldName = ""
            var code = ""
            var name = ""
            var type = ""
            var precision = 0

            token = reader.peek()

            if (token == JsonToken.NAME) {
                reader.skipValue()
            }

            if (token == JsonToken.BEGIN_OBJECT) {
                token = reader.peek()
            }

            reader.beginObject()

            if (token == JsonToken.NAME) {
                fieldName = reader.nextName()
            }

            if (fieldName == "code") {
                code = reader.nextString()
                reader.peek()
                fieldName = reader.nextName()
            }

            if (fieldName == "name") {
                name = reader.nextString()
                reader.peek()
                fieldName = reader.nextName()
            }

            if (fieldName == "precision") {
                precision = reader.nextInt()
                reader.peek()
                fieldName = reader.nextName()
            }

            if (fieldName == "_type") {
                type = reader.nextString()
            }

            reader.endObject()

            currencies.add(CurrencyResponse(code, name, precision, type))
        }

        reader.endObject()

        return CurrenciesResponse(currencies)
    }
}