package com.amihaiemil.eoyaml;

import javax.json.JsonObject;
import javax.json.JsonValue;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Representation of a {@link javax.json.JsonObject} as YAML Mapping.
 * @author criske
 * @version $Id$
 * @since 5.1.7
 */
final class JsonYamlMapping extends BaseYamlMapping {

    /**
     * Json object being mapped.
     */
    private final JsonObject object;

    /**
     * Ctor.
     * @param object Json object being mapped.
     */
    JsonYamlMapping(final JsonObject object) {
        this.object = object;
    }

    @Override
    public Set<YamlNode> keys() {
        final Set<YamlNode> keys = new LinkedHashSet<>();
        this.object.keySet().forEach(key -> keys
            .add(new PlainStringScalar(key)));
        return keys;
    }

    @Override
    public YamlNode value(final YamlNode key) {
        final JsonValue jsonValue = this.object.get(key.asScalar().value());
        return new JsonYamlDump(jsonValue).dump();
    }

    @Override
    public Comment comment() {
        return new Comment() {
            @Override
            public YamlNode yamlNode() {
                return JsonYamlMapping.this;
            }

            @Override
            public String value() {
                return "";
            }

            @Override
            public boolean inlineComment() {
                return false;
            }
        };
    }
}
