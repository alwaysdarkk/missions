package com.github.alwaysdarkk.missions.common.mongodb;

import com.github.alwaysdarkk.missions.common.configuration.ConfigValue;
import com.github.alwaysdarkk.missions.common.mongodb.parser.MongoDocumentParser;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.ReplaceOptions;
import org.bson.BsonDocument;
import org.bson.UuidRepresentation;

import java.util.concurrent.TimeUnit;

public abstract class MongoRepository<V> {

    protected static final ReplaceOptions REPLACE_OPTIONS = new ReplaceOptions().upsert(true);

    protected final MongoDocumentParser<V> parser;

    protected MongoClient mongoClient;
    protected MongoCollection<BsonDocument> collection;

    protected MongoRepository(MongoDocumentParser<V> parser) {
        this.parser = parser;

        connection();
    }

    protected void connection() {
        final String connectionUrl = ConfigValue.get(ConfigValue::connectionUrl);

        final ConnectionString connectionString = new ConnectionString(connectionUrl);
        final MongoClientSettings clientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .uuidRepresentation(UuidRepresentation.STANDARD)
                .applyToConnectionPoolSettings(builder ->
                        builder.maxSize(20).minSize(1).maxConnecting(3).maxConnectionIdleTime(0, TimeUnit.MILLISECONDS))
                .retryWrites(true)
                .build();

        this.mongoClient = MongoClients.create(clientSettings);
        this.collection = mongoClient.getDatabase("global").getCollection("missions-users", BsonDocument.class);
    }
}