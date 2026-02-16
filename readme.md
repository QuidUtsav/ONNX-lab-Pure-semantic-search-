# 🧪 onnx-lab: Pure Scala Semantic Search (Zero-Python)

> **A Proof-of-Concept for running State-of-the-Art NLP models natively on the JVM using ONNX Runtime.**


## 🎯 The Mission
Most AI pipelines today rely on a heavy "Python Sidecar" architecture (FastAPI wrappers around PyTorch/TensorFlow). This introduces latency, deployment complexity, and language barriers.

**onnx-lab** proves that we can run deep learning inference **directly inside the JVM** with close-to-metal performance, bypassing Python entirely.

## 🚀 Key Features
* **Zero Python Dependencies:** Runs entirely on Scala 3 + JVM.
* **Direct JNI Integration:** Uses `onnxruntime` to bridge Scala and C++ for high-performance inference.
* **Custom Tokenizer:** A hand-written `SimpleTokenizer` to handle BERT vocabulary mapping without external NLP libraries.
* **Semantic Search:** Implements Cosine Similarity math natively to rank sentence embeddings.

---

## 🛠️ Installation & Usage

### Prerequisites
* **Scala 3** / **sbt**
* **Java 11+**

### Quick Start
1.  **Clone the repo:**
    ```bash
    # We clone into a specific folder name 'onnx-lab' for easier navigation
    git clone [https://github.com/QuidUtsav/ONNX-lab-Pure-semantic-search-](https://github.com/QuidUtsav/ONNX-lab-Pure-semantic-search-) onnx-lab
    cd onnx-lab
    ```

2.  **Download the Model:**
    Ensure your `models/` directory contains:
    * `model.onnx` (Quantized or standard BERT model)
    * `vocab.txt` (BERT vocabulary)

3.  **Run the Inference Engine:**
    ```bash
    sbt run
    ```

### Example Output
```text
[info] running run
Enter a sentence:
> The solar flare is intense.

Output vector length: 384
Most relevant fact: The sun is hot. with similarity 0.854