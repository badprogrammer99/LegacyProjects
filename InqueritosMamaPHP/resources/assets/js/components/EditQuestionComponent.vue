<template>
  <b-row>
    <b-col md="10" offset-md="1">
      <vue-snotify></vue-snotify>
      <b-card no-body>
        <div slot="header">Editar Questão</div>
        <b-card-body>
          <b-form @submit.prevent="editCurrentQuestion" v-if="show">
            <b-form-group label="Inquérito" label-for="inquerito_questao">
              <b-form-input
                name="inquerito_questao"
                disabled
                v-model="questionInformation.questionInquiry">
              </b-form-input>
            </b-form-group>
            <b-form-group label="Questionário" label-for="questionario_questao">
              <b-form-input
                name="questionario_questao"
                disabled
                v-model="questionInformation.questionQuestionnaire">
              </b-form-input>
            </b-form-group>
            <b-form-group label="Nome" label-for="nome_questao">
              <b-form-input
                name="nome_questao"
                required
                placeholder="Nome da Questão"
                v-model="editQuestion.questionName"> 
              </b-form-input>
            </b-form-group>
            <b-form-group label="Descrição" label-for="descricao_questao">
              <b-form-input
                name="descricao_questao"
                required
                placeholder="Descrição da Questão"
                v-model="editQuestion.questionDescription">
              </b-form-input>
            </b-form-group>
            <b-form-group 
              label="Número de respostas às quais o Utilizador poderá responder"
              label-for="numero_respostas_possiveis">
              <b-form-input
                name="numero_respostas_possiveis"
                required
                placeholder="Número de respostas às quais o Utilizador poderá responder"
                v-model="editQuestion.questionPossibleAnswerTimes">
              </b-form-input>
            </b-form-group>
            <b-form-group 
              label="Nome das respostas possíveis"
              label-for="nome_respostas_possiveis">
              <b-form-input
                name="nome_respostas_possiveis"
                required
                placeholder="Nome das respostas às quais o Utilizador poderá responder"
                v-model="editQuestion.questionPossibleAnswerNames">
              </b-form-input>
            </b-form-group>
            <b-form-group>
              <b-btn type="submit" variant="primary">Editar</b-btn>
            </b-form-group>
          </b-form>
        </b-card-body>
      </b-card>
    </b-col>
  </b-row>
</template>

<script>
export default {
  data() {
    return {
      urls: {
        inquiriesURL: this.$attrs.inquiriesBaseURL,
        questionsURL: this.$attrs.questionsBaseURL
      },

      questionInformation: {
        questionInquiryId: null,
        questionInquiry: null,
        questionQuestionnaireId: null,
        questionQuestionnaire: null
      },

      editQuestion: {
        questionId: null,
        questionName: '',
        questionDescription: '',
        questionPossibleAnswerTimes: null,
        questionPossibleAnswerNames: null,
      },

      show: true
    };
  },

  created() {
    this.fetchQuestionDetails();
  },

  methods: {
    fetchQuestionDetails: function() {
      window.axios
        .get(
          this.urls.inquiriesURL +
            this.$route.params.inquiryId +
            "/questionnaries/" +
            this.$route.params.questionnaireId +
            "/questions/" +
            this.$route.params.questionId
          )
        .then(response => {
          this.questionInformation.questionInquiryId = response.data.data.questionnaire.inquiry.id;
          this.questionInformation.questionInquiry = response.data.data.questionnaire.inquiry.name;
          this.questionInformation.questionQuestionnaireId = response.data.data.questionnaire.id;
          this.questionInformation.questionQuestionnaire = response.data.data.questionnaire.name;
          this.editQuestion.questionId = response.data.data.id;
          this.editQuestion.questionName = response.data.data.name;
          this.editQuestion.questionDescription = response.data.data.description;
          this.editQuestion.questionPossibleAnswerTimes = response.data.data["possible-answers"];
          this.editQuestion.questionPossibleAnswerNames = (response.data.data["possible-answer-names"]).toString();
        });
    },

    editCurrentQuestion: function() {
      window.axios.put(this.urls.questionsURL + this.editQuestion.questionId, {
        id: this.editQuestion.questionId,
        inquiry_id: this.editQuestion.questionInquiryId,
        questionnaire_id: this.editQuestion.questionQuestionnaireId,
        name: this.editQuestion.questionName,
        description: this.editQuestion.questionDescription,
        possibleAnswers: this.editQuestion.questionPossibleAnswerTimes,
        possibleAnswerNames: this.editQuestion.questionPossibleAnswerNames
      }).then(response => {
        this.resetForm();
      });
    },

    resetForm: function() {
      this.questionInformation.questionInquiryId = null;
      this.questionInformation.questionInquiry = null;
      this.questionInformation.questionQuestionnaireId = null;
      this.questionInformation.questionQuestionnaire = null;
      this.editQuestion.questionId = null;
      this.editQuestion.questionName = '';
      this.editQuestion.questionDescription = '';
      this.editQuestion.questionPossibleAnswerTimes = null;
      this.editQuestion.questionPossibleAnswerNames = null;
      this.show = false;
      this.$nextTick(() => {
        this.show = true;
      });
    },
  }
};
</script>
