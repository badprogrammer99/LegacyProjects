using System.ComponentModel.DataAnnotations.Schema;

namespace QuestionarioC_.Models {
    public class Answer {
        public int QuestionId { get; set; }
        [ForeignKey("QuestionId")]
        public Question Question { get; set; }
        
        public int UserId { get; set; }
        [ForeignKey("UserId")]
        public User User { get; set; }
        
        public string Observations { get; set; }
    }
}