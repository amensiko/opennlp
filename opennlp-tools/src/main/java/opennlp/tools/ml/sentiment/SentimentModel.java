package opennlp.tools.ml.sentiment;

import java.util.Map;

import opennlp.tools.ml.model.MaxentModel;
import opennlp.tools.util.model.BaseModel;

public class SentimentModel extends BaseModel {

	private static final String COMPONENT_NAME = "DocumentSentimentME";
	private static final String MODEL_ENTRY_NAME = "sentiment.model";

	public SentimentModel(String languageCode, MaxentModel model,
			Map<String, String> manifestInfoEntries) {
		super(COMPONENT_NAME, languageCode, manifestInfoEntries);

		artifactMap.put(MODEL_ENTRY_NAME, model);
		checkArtifactMap();
	}

}
