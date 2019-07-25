using System.Collections.Generic;
using System.Threading.Tasks;

namespace QuestionarioC_.Interfaces.Repositories
{
    public interface IAbstractRepository<T> where T : class
    {
        ICollection<T> GetAll();
        T GetById(int id);
        Task<bool> Add(T obj);
        Task<bool> Update(int id, T obj);
        Task<bool> Delete(int id);
    }
}