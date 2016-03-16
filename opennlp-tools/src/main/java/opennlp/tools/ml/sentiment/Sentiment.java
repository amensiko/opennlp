package opennlp.tools.ml.sentiment;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import opennlp.tools.doccat.DocumentSample;
import opennlp.tools.doccat.DocumentSampleStream;
import opennlp.tools.doccat.FeatureGenerator;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;
import opennlp.tools.util.model.ModelUtil;

public class Sentiment {
	private static final String[] STOP_WORDS = new String[] { "a", "the", "are", "to", "be", "that"};
	static {
		Arrays.sort(STOP_WORDS);
	}
	
	private static FeatureGenerator defaultFeatureGenerator = new FeatureGenerator() {
		
		
		@Override
		public Collection<String> extractFeatures(String[] text,
				Map<String, Object> extraInformation) {
			Collection<String> bagOfWords = new ArrayList<String>(text.length);
			int index;
			for (String word : text) {
				index = Arrays.binarySearch(STOP_WORDS, word);
				if(index < 0) {
					bagOfWords.add("word=" + word);
				}
			}

			return bagOfWords;
		}

	};

	public static void main(String[] args) {
		SentimentModel model = null;

		InputStream dataIn = null;
		try {
			dataIn = new FileInputStream("en-sentiment.train");
			ObjectStream<String> lineStream = new PlainTextByLineStream(dataIn,
					"UTF-8");

			ObjectStream<DocumentSample> sampleStream = new DocumentSampleStream(
					lineStream);

			model = DocumentSentimentME.train("en", sampleStream,
					ModelUtil.createDefaultTrainingParameters(),
					defaultFeatureGenerator);
		} catch (IOException e) {
			// Failed to read or parse training data, training failed
			e.printStackTrace();
		} finally {
			if (dataIn != null) {
				try {
					dataIn.close();
				} catch (IOException e) {
					// Not an issue, training already finished.
					// The exception should be logged and investigated
					// if part of a production system.
					e.printStackTrace();
				}
			}
		}

	}
}
