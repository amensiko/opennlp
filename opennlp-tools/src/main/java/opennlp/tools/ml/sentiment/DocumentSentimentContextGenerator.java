package opennlp.tools.ml.sentiment;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;

import opennlp.tools.doccat.FeatureGenerator;

public class DocumentSentimentContextGenerator {

	private FeatureGenerator[] mFeatureGenerators;

	DocumentSentimentContextGenerator(FeatureGenerator[] featureGenerators) {
		mFeatureGenerators = featureGenerators;
	}

	public String[] getContext(String[] text,
			Map<String, Object> extraInformation) {
		Collection<String> context = new LinkedList<String>();

	    for (int i = 0; i < mFeatureGenerators.length; i++) {
	      Collection<String> extractedFeatures =
	          mFeatureGenerators[i].extractFeatures(text, extraInformation);
	      context.addAll(extractedFeatures);
	    }

	    return context.toArray(new String[context.size()]);
	}

}
