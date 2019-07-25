using System.ComponentModel.DataAnnotations.Schema;

namespace QuestionarioC_.Models
{
    public class QuestionAnswerChoice
    {
        public int QuestionId { get; set; }
        [ForeignKey("QuestionId")]
        public ChoiceQuestion Question { get; set; }
        
        public int AnswerChoiceId { get; set; }
        [ForeignKey("AnswerChoiceId")]
        public AnswerChoice AnswerChoice { get; set; }
    }
}