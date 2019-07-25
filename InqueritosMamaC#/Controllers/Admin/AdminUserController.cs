using System.Threading.Tasks;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using QuestionarioC_.Helpers;
using QuestionarioC_.Interfaces.Repositories;
using QuestionarioC_.Models;

namespace QuestionarioC_.Controllers.Admin
{
    [Authorize("Admin")]
    [Route("api/admin/User")]
    public class AdminUserController : Controller
    {
        private readonly IUserRepository _userRepository;

        public AdminUserController(IUserRepository userRepository)
        {
            _userRepository = userRepository;
        }

        [HttpGet("[action]")]
        public IActionResult AdminMethod() {
            return Json(new { message = "Admin method!" });
        }

        [HttpPost("[action]")]
        public async Task<IActionResult> CreateNewUser(int id, string name, string password, bool isAdmin)
        {
            IActionResult actionResult = null;

            var user = new User
            {
                PersonalId = id,
                Name = name,
                Password = BCryptHelper.HashPassword(password),
                IsAdmin = isAdmin
            };

            var addUserResult = await _userRepository.Add(user);

            if (addUserResult)
            {
                actionResult = Json(new {status = "The user has been created successfully."});
            }

            return actionResult;
        }
    }
}