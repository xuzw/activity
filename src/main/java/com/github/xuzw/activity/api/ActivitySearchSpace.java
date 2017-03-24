package com.github.xuzw.activity.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.ResourceIterator;
import org.neo4j.graphdb.Transaction;

import com.github.xuzw.activity.model.Activity;
import com.github.xuzw.activity.model.ActivityBuilder;
import com.github.xuzw.entity.api.EntitySearchSpace;

/**
 * @author 徐泽威 xuzewei_2012@126.com
 * @time 2017年3月23日 下午6:26:39
 */
public class ActivitySearchSpace {
    public static final Label label_activity = Label.label("activity");
    public static final String relationship_type_name_source = "source";
    public static final String relationship_type_name_target = "target";
    private String workPath;
    private EntitySearchSpace entitySearchSpace;
    private GraphDatabaseService graphDb;

    public ActivitySearchSpace(String workPath) {
        this.workPath = workPath;
        entitySearchSpace = new EntitySearchSpace(workPath);
        graphDb = entitySearchSpace.getGraphDb();
    }

    public String getWorkPath() {
        return workPath;
    }

    public EntitySearchSpace getEntitySearchSpace() {
        return entitySearchSpace;
    }

    public GraphDatabaseService getGraphDb() {
        return graphDb;
    }

    public List<Activity> search(TimestampRange timestampRange) {
        List<Activity> activities = new ArrayList<>();
        try (Transaction tx = graphDb.beginTx()) {
            ResourceIterator<Node> iterator = graphDb.findNodes(label_activity);
            while (iterator.hasNext()) {
                Node node = iterator.next();
                long timestamp = (long) node.getProperty("timestamp", 0);
                long timestampEnd = timestamp + (long) node.getProperty("dur", 0);
                if (timestamp < timestampRange.getBeginTimestamp() || timestampEnd > timestampRange.getEndTimestamp()) {
                    continue;
                }
                ActivityBuilder builder = new ActivityBuilder();
                builder.type((String) node.getProperty("type", "unknow"));
                builder.effect((String) node.getProperty("effect", "unknow"));
                builder.expectEffect((String) node.getProperty("expectEffect", "unknow"));
                builder.timestamp(timestamp);
                builder.expectTimestamp((long) node.getProperty("expectTimestamp", 0));
                builder.dur((long) node.getProperty("dur", 0));
                builder.expectDur((long) node.getProperty("expectDur", 0));
                builder.target((String) node.getProperty("target", "unknow"));
                if (node.hasProperty("sources")) {
                    String[] sources = (String[]) node.getProperty("sources");
                    for (String source : sources) {
                        builder.source(source);
                    }
                }
                builder.locale((String) node.getProperty("locale", "unknow"));
                builder.uuid((String) node.getProperty("uuid", "unknow"));
                activities.add(builder.build());
            }
            tx.success();
        }
        return activities;
    }

    private static boolean _isBlank(List<String> strings) {
        return strings == null || strings.isEmpty();
    }

    public void load(Activity activity) throws UnknownEntityException {
        String target = activity.getTarget();
        try (Transaction tx = graphDb.beginTx()) {
            if (graphDb.findNode(label_activity, "uuid", activity.getUuid()) != null) {
                return;
            }
            Node node = graphDb.createNode(label_activity);
            Node targetNode = entitySearchSpace.findNode(target);
            if (targetNode == null) {
                throw new UnknownEntityException(target);
            }
            node.createRelationshipTo(targetNode, RelationshipType.withName(relationship_type_name_target));
            for (String source : activity.getSources()) {
                Node sourceNode = entitySearchSpace.findNode(source);
                if (sourceNode == null) {
                    throw new UnknownEntityException(source);
                }
                sourceNode.createRelationshipTo(node, RelationshipType.withName(relationship_type_name_source));
            }
            node.setProperty("type", activity.getType());
            if (StringUtils.isNotBlank(activity.getEffect())) {
                node.setProperty("effect", activity.getEffect());
            }
            if (StringUtils.isNotBlank(activity.getExpectEffect())) {
                node.setProperty("expectEffect", activity.getExpectEffect());
            }
            node.setProperty("timestamp", activity.getTimestamp());
            node.setProperty("expectTimestamp", activity.getExpectTimestamp());
            node.setProperty("dur", activity.getDur());
            node.setProperty("expectDur", activity.getExpectDur());
            if (StringUtils.isNotBlank(activity.getTarget())) {
                node.setProperty("target", activity.getTarget());
            }
            List<String> sources = activity.getSources();
            if (!_isBlank(sources)) {
                node.setProperty("sources", sources.toArray(new String[0]));
            }
            if (StringUtils.isNotBlank(activity.getLocale())) {
                node.setProperty("locale", activity.getLocale());
            }
            if (StringUtils.isNotBlank(activity.getUuid())) {
                node.setProperty("uuid", activity.getUuid());
            }
            tx.success();
        }
    }

    public void loadActivityRepository(String activityRepository) throws IOException, ActivityRepositoryFileFormatException, UnknownEntityException {
        ActivityRepositoryReader repositoryReader = new ActivityRepositoryReader(activityRepository);
        Activity activity = null;
        while ((activity = repositoryReader.read()) != null) {
            load(activity);
        }
        repositoryReader.close();
    }

    public void shutdown() {
        entitySearchSpace.shutdown();
        graphDb.shutdown();
    }
}
