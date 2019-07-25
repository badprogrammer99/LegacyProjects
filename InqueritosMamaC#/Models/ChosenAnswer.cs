using System.ComponentModel.DataAnnotations.Schema;

namespace QuestionarioC_.Models
{
    public class ChosenAnswer : Answer
    {
        public int AnswerChoiceId { get; set; }
        [ForeignKey("AnswerChoiceId")]
        public AnswerChoice AnswerChoice { get; set; }
    }
}