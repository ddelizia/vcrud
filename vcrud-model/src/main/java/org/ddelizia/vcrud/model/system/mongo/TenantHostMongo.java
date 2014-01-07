package org.ddelizia.vcrud.model.system.mongo;

import com.mongodb.MongoOptions;
import com.mongodb.ServerAddress;
import com.mongodb.WriteConcern;
import org.ddelizia.vcrud.model.system.TenantHost;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 04/01/14
 * Time: 16:03
 * To change this template use File | Settings | File Templates.
 */
public class TenantHostMongo extends TenantHost{

    private MongoOptions mongoOptions;

    private WriteConcern writeConcern;

    private List<ServerAddress> replicaSetSeeds;

    private List<ServerAddress> replicaPair;

    public MongoOptions getMongoOptions() {
        return mongoOptions;
    }

    public void setMongoOptions(MongoOptions mongoOptions) {
        this.mongoOptions = mongoOptions;
    }

    public WriteConcern getWriteConcern() {
        return writeConcern;
    }

    public void setWriteConcern(WriteConcern writeConcern) {
        this.writeConcern = writeConcern;
    }

    public List<ServerAddress> getReplicaSetSeeds() {
        return replicaSetSeeds;
    }

    public void setReplicaSetSeeds(List<ServerAddress> replicaSetSeeds) {
        this.replicaSetSeeds = replicaSetSeeds;
    }

    public List<ServerAddress> getReplicaPair() {
        return replicaPair;
    }

    public void setReplicaPair(List<ServerAddress> replicaPair) {
        this.replicaPair = replicaPair;
    }
}
