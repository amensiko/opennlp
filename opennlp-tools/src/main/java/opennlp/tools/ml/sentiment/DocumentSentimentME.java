package opennlp.tools.ml.sentiment;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import opennlp.tools.doccat.DocumentCategorizerEventStream;
import opennlp.tools.doccat.DocumentSample;
import opennlp.tools.doccat.FeatureGenerator;
import opennlp.tools.ml.EventTrainer;
import opennlp.tools.ml.TrainerFactory;
import opennlp.tools.ml.TrainerFactory.TrainerType;
import opennlp.tools.ml.model.MaxentModel;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.TrainingParameters;

public class DocumentSentimentME implements DocumentSentiment {

	public static SentimentModel train(String languageCode,
			ObjectStream<DocumentSample> samples, TrainingParameters mlParams,
			FeatureGenerator... featureGenerators) throws IOException {

		Map<String, String> settings = mlParams.getSettings();
		Map<String, String> manifestInfoEntries = new HashMap<String, String>();
		TrainerType trainerType = TrainerFactory.getTrainerType(settings);
//		if (!EventTrainer.EVENT_VALUE.equals(trainerType)) {
//			throw new IllegalArgumentException("EventTrain is not supported");
//		}

		EventTrainer trainer = TrainerFactory.getEventTrainer(
				mlParams.getSettings(), manifestInfoEntries);
		MaxentModel model = trainer.train(new DocumentSentimentEventStream(
				samples, featureGenerators));

		return new SentimentModel(languageCode, model, manifestInfoEntries);
	}

}
