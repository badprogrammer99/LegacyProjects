using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace QuestionarioC_.Models {
    public class Inquiry {
        [Key]
        [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
        public int InquiryId { get; set; }
        public string Name { get; set; }
        public ICollection<Questionnaire> Questionnaires { get; set; }
    }
}