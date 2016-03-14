package opennlp.tools.ml.sentiment;

import java.util.Iterator;

import opennlp.tools.doccat.DocumentSample;
import opennlp.tools.doccat.FeatureGenerator;
import opennlp.tools.ml.model.Event;
import opennlp.tools.util.AbstractEventStream;
import opennlp.tools.util.ObjectStream;

public class DocumentSentimentEventStream extends AbstractEventStream<DocumentSample> {
	
	private DocumentSentimentContextGenerator mContextGenerator;

	public DocumentSentimentEventStream(ObjectStream<DocumentSample> data,
			FeatureGenerator[] featureGenerators) {
		super(data);

	    mContextGenerator =
	      new DocumentSentimentContextGenerator(featureGenerators);
	}

	@Override
	protected Iterator<Event> createEvents(DocumentSample sample) {
		return new Iterator<Event>(){

		      private boolean isVirgin = true;

		      public boolean hasNext() {
		        return isVirgin;
		      }

		      public Event next() {

		        isVirgin = false;

		        return new Event(sample.getCategory(),
		            mContextGenerator.getContext(sample.getText(), sample.getExtraInformation()));
		      }

		      public void remove() {
		        throw new UnsupportedOperationException();
		      }};
	}

}
