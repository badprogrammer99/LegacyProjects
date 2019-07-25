using System.Collections.Generic;

namespace QuestionarioC_.Models
{
    public class ChoiceQuestion : Question
    {
        public int PossibleAnswers { get; set; }
        public ICollection<QuestionAnswerChoice> QuestionAnswerChoices { get; set; }
    }
}