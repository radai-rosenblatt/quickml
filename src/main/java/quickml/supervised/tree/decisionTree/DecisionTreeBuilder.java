package quickml.supervised.tree.decisionTree;

import com.google.common.collect.Lists;
import org.javatuples.Pair;
import quickml.data.ClassifierInstance;
import quickml.data.PredictionMap;
import quickml.supervised.tree.TreeBuilder;
import quickml.supervised.tree.decisionTree.treeBuildContexts.DTreeContextBuilder;
import quickml.supervised.tree.decisionTree.nodes.DTNode;

import java.util.Map;
import java.util.Set;

/**
 * Created by alexanderhawk on 6/20/15.
 */
public class DecisionTreeBuilder<I extends ClassifierInstance> implements TreeBuilder<PredictionMap, Object, I> {
    private DTreeContextBuilder<I> treeContextBuilder;

    public DecisionTreeBuilder(DTreeContextBuilder<I> treeContextBuilder) {
        this.treeContextBuilder = treeContextBuilder;
    }

    @Override
    public DecisionTree buildPredictiveModel(Iterable<I> trainingData) {
        DecisionTreeBuilderHelper<I> treeBuilderHelper = new DecisionTreeBuilderHelper<>(treeContextBuilder);
        Pair<DTNode, Set<Object>> rootAndClassifications = treeBuilderHelper.computeNodesAndClasses(Lists.newArrayList(trainingData));
        DTNode root = rootAndClassifications.getValue0();
        Set<Object> classifications = rootAndClassifications.getValue1();
        return new DecisionTree(root, classifications);
    }

    @Override
    public void updateBuilderConfig(Map<String, Object> config) {
        treeContextBuilder.update(config);
    }

    @Override
    public DecisionTreeBuilder<I> copy() {
        return new DecisionTreeBuilder<>(treeContextBuilder.copy());
    }
}
