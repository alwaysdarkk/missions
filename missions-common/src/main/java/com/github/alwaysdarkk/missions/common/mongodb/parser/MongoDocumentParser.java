package com.github.alwaysdarkk.missions.common.mongodb.parser;

import org.bson.BsonDocument;
import org.jetbrains.annotations.Nullable;

public abstract class MongoDocumentParser<V> {
    public abstract BsonDocument write(V v);

    public abstract @Nullable V read(BsonDocument document);
}