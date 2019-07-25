using Microsoft.AspNetCore.Mvc;
using QuestionarioC_.Helpers;
using QuestionarioC_.Interfaces.Repositories;

namespace QuestionarioC_.Controllers
{
    [Route("api")]
    public class LoginController : Controller
    {
        private readonly IUserRepository _userRepository;

        public LoginController(IUserRepository userRepository)
        {
            _userRepository = userRepository;
        }

        [HttpPost]
        [Route("login")]
        public IActionResult VerifyUser(int id, string password)
        {
            var user = _userRepository.GetUserByPersonalId(id);

            if (user != null && BCryptHelper.ValidatePassword(password, user.Password))
            {
                var token = _userRepository.GenerateUserToken(user);
                return Json(new {Logged = true, Admin = user.IsAdmin, Token = token});
            }

            return new JsonResult(new {status = "Incorrect user or password, please try again"})
            {
                StatusCode = 401
            };
        }
    }
}