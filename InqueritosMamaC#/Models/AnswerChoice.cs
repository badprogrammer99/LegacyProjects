using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace QuestionarioC_.Models {
    public class AnswerChoice {
        [Key]
        [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
        public int AnswerChoiceId { get; set; }
        public string Name { get; set; }
    }
}