package com.meifute.core.data.json.element;

import com.meifute.core.data.json.FromJsonStringAble;
import com.meifute.core.data.json.MftJsonException;
import com.meifute.core.lang.MftStrings;

import java.util.Objects;

/**
 * @author mac
 */
public class MftJsonString extends AbstractJsonElement implements Comparable<MftJsonString>, FromJsonStringAble {

    public static final String QUOTATION = "\"";
    String string;

    public MftJsonString(String s) {
        this.string = s;
    }

    @Override
    public String toJsonString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\"");
        for (char c : string.toCharArray()) {
            if (c == '"') {
                sb.append("\\").append('"');
            } else if (c == '\\') {
                sb.append('\\').append('\\');
            } else if (c == '\b') {
                sb.append('\\').append('b');
            } else if (c == '\f') {
                sb.append('\\').append('f');
            } else if (c == '\n') {
                sb.append('\\').append('n');
            } else if (c == '\r') {
                sb.append('\\').append('r');
            } else if (c == '\t') {
                sb.append('\\').append('t');
            } else {
                sb.append(c);
            }
        }
        sb.append('"');
        return sb.toString();
    }

    @Override
    public String toString() {
        return this.string;
    }

    @Override
    public void parse(String jsonString) throws MftJsonException {
        String json = jsonString.trim();
        if (json.startsWith(QUOTATION) && json.endsWith(QUOTATION)) {
            this.string = json.substring(1, json.length() - 1);
        } else {
            throw new MftJsonException("A json string  should start with \" and end with \":"
                    + MftStrings.abbreviate(jsonString, "...", 10));
        }
    }

    @Override
    public int compareTo(MftJsonString o) {
        return this.string.compareTo(o.string);

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MftJsonString)) {
            return false;
        }
        MftJsonString that = (MftJsonString) o;
        return string.equals(that.string);
    }

    @Override
    public int hashCode() {
        return Objects.hash(string);
    }

}
