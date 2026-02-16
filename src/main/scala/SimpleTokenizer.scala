import scala.io.Source

class SimpleTokenizer(vocabFile: String) {
    private  val vocab = Source.fromFile(vocabFile).getLines().zipWithIndex.toMap

    def encode(text: String): Array[Long] = {
    // A. Normalization (Make it lowercase)
    // BERT is "uncased", meaning it treats "Hello" and "hello" the same.
    val lower = text.toLowerCase()

    // B. Tokenization (Split by space)
    // We split "hello world" -> ["hello", "world"]
    // We map each word to its ID from our vocab table.
    // If a word isn't found (like "ChatGPT"), we use 100 ([UNK] - Unknown).
    val tokens = lower.split("\\s+").map { word =>
      vocab.getOrElse(word, vocab.getOrElse("[UNK]", 100)).toLong
    }

    // C. Add Special Tokens
    // BERT always expects [CLS] (101) at the start and [SEP] (102) at the end.
    Array(101L) ++ tokens ++ Array(102L)
  }

}

