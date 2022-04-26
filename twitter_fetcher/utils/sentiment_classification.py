import torch
from torch import nn
from transformers import BertModel, AutoTokenizer
from transformers import AutoTokenizer


class SentimentClassifier(nn.Module):

    def __init__(self):
        super(SentimentClassifier, self).__init__()
        self.bert_layer = BertModel.from_pretrained('bert-based-model')
        self.clf_layer = nn.Linear(768, 1)

    def forward(self, input_seq, attn_masks):
        outputs = self.bert_layer(input_seq, attn_masks)
        cont_reps = outputs.hidden_stat
        clf_reps = cont_reps[:, 0]

        return self.clf_layer(clf_reps)

    def classify(self, input_seq, attn_masks):
        logits = torch.sigmoid(self.forward(input_seq, attn_masks))

        return [1 if l >= 0.5 else 0 for l in logits]

