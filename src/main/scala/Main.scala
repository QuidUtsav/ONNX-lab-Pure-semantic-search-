import ai.onnxruntime.{OrtEnvironment, OrtSession, OnnxTensor}
import java.nio.FloatBuffer
import scala.jdk.CollectionConverters._
import scala.io.Source
import java.nio.LongBuffer

@main def run(): Unit ={

  val env = OrtEnvironment.getEnvironment
  val session = env.createSession("models/model.onnx", new OrtSession.SessionOptions())
  val tokenizer = new SimpleTokenizer("models/vocab.txt")

  val facts = List(
    "The capital of Nepal is Kathmandu.",
    "The capital of Germany is Berlin.",
    "The sun is hot."
  )
  val factEmbeddings = facts.map{fact=>
    (fact, getEmbedding(fact, session, env, tokenizer))
    }

  println("Enter a sentence:")
  val inputText = scala.io.StdIn.readLine()
  
  val queryVector = getEmbedding(inputText, session, env, tokenizer)
  println("Output vector length: " + queryVector.length)


  val similarities = factEmbeddings.map{case(fact, embedding)=>
    (fact, cosineSimilarity(queryVector, embedding))
  }
  val sortedFacts = similarities.sortBy(-_._2)
  println("Most relevant fact: " + sortedFacts.head._1 + " with similarity "+ sortedFacts.head._2)

  session.close()
  env.close()
}

def getEmbedding(text: String, session: OrtSession, env: OrtEnvironment, tokenizer: SimpleTokenizer): Array[Float] = {
  try{
  val tokens = tokenizer.encode(text)
  val sequenceLength = tokens.length
  val shape = Array(1L, sequenceLength.toLong)

  val ones = Array.fill(sequenceLength)(1L)
  val zeros = Array.fill(sequenceLength)(0L)

  val inputIensors = OnnxTensor.createTensor(env, LongBuffer.wrap(tokens),shape)
  val maskTensor = OnnxTensor.createTensor(env, LongBuffer.wrap(ones),shape)
  val typeTensor = OnnxTensor.createTensor(env, LongBuffer.wrap(zeros),shape)

  val inputs= Map(
    "input_ids"-> inputIensors,
    "attention_mask"-> maskTensor,
    "token_type_ids"-> typeTensor
  ).asJava

  val results = session.run(inputs)
  val output = results.get(0).getValue.asInstanceOf[Array[Array[Array[Float]]]](0)(0)
  results.close()
  return output

  } catch {
    case e: Exception =>
      println("Error during embedding generation: " + e.getMessage)
      Array.emptyFloatArray
  }


}
def cosineSimilarity(vecA : Array[Float], vecB: Array[Float]): Double ={
  val dotProduct = vecA.zip(vecB).map{case(a,b)=>a*b}.sum
  val magnitudeA = math.sqrt(vecA.map(x=>x*x).sum)
  val magnitudeB = math.sqrt(vecB.map(x=>x*x).sum)
  if(magnitudeA == 0 || magnitudeB == 0) 0.0 else dotProduct / (magnitudeA * magnitudeB)
}
