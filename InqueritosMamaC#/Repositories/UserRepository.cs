using System;
using Microsoft.EntityFrameworkCore;
using System.Collections.Generic;
using System.IdentityModel.Tokens.Jwt;
using System.Linq;
using System.Security.Claims;
using System.Text;
using System.Threading.Tasks;
using Microsoft.Extensions.Options;
using Microsoft.IdentityModel.Tokens;
using QuestionarioC_.Helpers;
using QuestionarioC_.Interfaces.Repositories;
using QuestionarioC_.Models;

namespace QuestionarioC_.Repositories
{
    public class UserRepository : AbstractRepository<User>, IUserRepository
    {
        private readonly AppSecret _appSecret;

        public UserRepository(IOptions<AppSecret> appSecret)
        {
            _appSecret = appSecret.Value;
        }

        public User GetUserByPersonalId(int id)
        {
            return _db.Users.First(u => u.PersonalId == id);
        }

        public async Task<bool> SetUserPassword(int personalId, string oldPassword, string newPassword)
        {
            var user = _db.Users.First(u => u.PersonalId == personalId && u.Password == oldPassword);

            user.Password = BCryptHelper.HashPassword(newPassword);

            return await _db.SaveChangesAsync() > 0;
        }

        public async Task<ICollection<User>> FetchUserInquiries(int id)
        {
            var fetchInquiriesTask = Task.Run(() => _db.Users
                .Include(u => u.UserInquiries)
                .ThenInclude(ui => ui.Inquiry)
                .ThenInclude(i => i.Questionnaires)
                .ThenInclude(q => q.Questions)
                .ThenInclude(cq => (cq as ChoiceQuestion).QuestionAnswerChoices)
                .ThenInclude(ac => ac.AnswerChoice)
                .Where(user => user.UserId == id));

            var userInquiries = await fetchInquiriesTask;

            return userInquiries.ToList();
        }

        public string GenerateUserToken(User user)
        {
            var tokenHandler = new JwtSecurityTokenHandler();
            var key = Encoding.ASCII.GetBytes(_appSecret.Secret);
            var tokenDescriptor = new SecurityTokenDescriptor
            {
                Subject = new ClaimsIdentity(new Claim[]
                {
                    new Claim(ClaimTypes.Name, user.PersonalId.ToString()),
                    new Claim(ClaimTypes.Role, user.IsAdmin ? "Admin" : "User")
                }),
                Expires = DateTime.UtcNow.AddDays(1),
                SigningCredentials = new SigningCredentials(new SymmetricSecurityKey(key),
                    SecurityAlgorithms.HmacSha256Signature)
            };

            var token = tokenHandler.CreateToken(tokenDescriptor);

            return tokenHandler.WriteToken(token);
        }
    }
}