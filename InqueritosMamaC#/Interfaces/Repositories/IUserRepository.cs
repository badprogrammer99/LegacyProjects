using System.Collections.Generic;
using System.Threading.Tasks;
using QuestionarioC_.Models;

namespace QuestionarioC_.Interfaces.Repositories
{
    public interface IUserRepository : IAbstractRepository<User>
    {
        User GetUserByPersonalId(int id);
        Task<bool> SetUserPassword(int personalId, string oldPassword, string newPassword);
        Task<ICollection<User>> FetchUserInquiries(int personalId);
        string GenerateUserToken(User user);
    }
}