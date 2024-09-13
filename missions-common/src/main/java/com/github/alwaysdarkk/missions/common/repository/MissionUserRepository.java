package com.github.alwaysdarkk.missions.common.repository;

import com.github.alwaysdarkk.missions.common.data.MissionUser;
import com.github.alwaysdarkk.missions.common.mongodb.MongoRepository;
import com.github.alwaysdarkk.missions.common.repository.parser.MissionUserParser;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;
import com.mongodb.client.model.ReplaceOneModel;
import org.bson.BsonDocument;

import java.util.List;
import java.util.stream.Collectors;

import static com.mongodb.client.model.Filters.eq;

public class MissionUserRepository extends MongoRepository<MissionUser> {

    public MissionUserRepository() {
        super(new MissionUserParser());

        collection.createIndex(
                Indexes.ascending("playerName"),
                new IndexOptions()
                        .name("playerName")
                        .unique(true)
                        .background(true)
                        .sparse(true)
        );
    }

    public void insert(MissionUser user) {
        collection.replaceOne(eq("playerName", user.getPlayerName()), parser.write(user), REPLACE_OPTIONS);
    }

    public void bulkInsert(List<MissionUser> users) {
        final List<ReplaceOneModel<BsonDocument>> models = users.stream()
                .map(user -> new ReplaceOneModel<>(
                        eq("playerName", user.getPlayerName()), parser.write(user), REPLACE_OPTIONS))
                .collect(Collectors.toList());

        collection.bulkWrite(models);
    }

    public MissionUser find(String playerName) {
        final BsonDocument document = collection.find(eq("playerName", playerName)).first();
        if (document == null) {
            return null;
        }

        return parser.read(document);
    }
}