using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.EntityFrameworkCore;
using QuestionarioC_.Interfaces.Repositories;
using QuestionarioC_.Models.Context;

namespace QuestionarioC_.Repositories
{
    public abstract class AbstractRepository<T> : IAbstractRepository<T> where T : class
    {
        protected readonly ApplicationContext _db;

        protected AbstractRepository()
        {
            _db = new ApplicationContext();
        }

        public virtual ICollection<T> GetAll()
        {
            return _db.Set<T>().AsNoTracking().ToList();
        }

        public virtual T GetById(int id)
        {
            return _db.Set<T>().Find(id);
        }

        public virtual async Task<bool> Add(T obj)
        {
            _db.Set<T>().Add(obj);

            return await _db.SaveChangesAsync() > 0;
        }

        public virtual async Task<bool> Update(int id, T obj)
        {
            var objRecord = GetById(id);

            objRecord = obj;

            _db.Set<T>().Update(objRecord);

            return await _db.SaveChangesAsync() > 0;
        }

        public virtual async Task<bool> Delete(int id)
        {
            var T = _db.Set<T>().Find(id);

            _db.Set<T>().Remove(T);

            return await _db.SaveChangesAsync() > 0;
        }
    }
}