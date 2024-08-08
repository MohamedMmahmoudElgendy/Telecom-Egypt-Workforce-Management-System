package com.example.ordercreate.services;


import com.example.ordercreate.entity.entity;
import com.example.ordercreate.repo.repo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class service {
    private final repo Repo;

    public List<entity> getallorders()
    {
        return Repo.findAll();
    }

    public Optional<entity> getorderid(long id)
    {
        return Repo.findById(id);
    }

    public List<entity> searchbystring ( String search)
    {
        int flag1=0;
        int flag2=0;
        for (int i=0;i<search.length();i++)
        {
            Boolean flag=Character.isDigit(search.charAt(i));
            if (flag)
            {
                flag1=1;
            }
            else
            {
                flag2=1;
            }
        }
        if (flag1==1 &&flag2==1)
        {
            String[] part = search.split("(?<=\\D)(?=\\d)");
            return  Repo.findbyname(part[0]);

        }
        else if(flag2==0 && flag1==1) {
            Long num = (long) Integer.parseInt(search);
            int numberofdigit = String.valueOf(num).length();
            if (numberofdigit == 10) {
                if (num != null) {
                    return Repo.findbyphonenumber(num);
                }
            } else if (numberofdigit < 11) {
                if (num != null) {
                    return  Repo.findById(num).map(Collections::singletonList).orElse(Collections.emptyList());
                }
            }
        }
        else if (flag2==1 && flag1==0)
        {
            return  Repo.findbyname(search);
        }
        else
        {
            return Collections.emptyList();
        }
        return null;

    }

    public List<entity> serachorders(Long id , String cname , Long cnumber)
    {
        if (id != null )
        {
            return Repo.findById(id).map(Collections::singletonList).orElse(Collections.emptyList());
        }
        else if (cname != null )
        {
            return Repo.findbyname(cname);
        }
        else if (cnumber!=null)
        {
            return Repo.findbyphonenumber(cnumber);
        }
        else {
            return Collections.emptyList();
        }

    }

    public long createorder(entity model)
    {
        model.setOrder_status("started");
        return Repo.save(model).getId();
    }


    public ResponseEntity<?> updateorder(Long id, entity en)
    {
        if(Repo.existsById(id))
        {
            Repo.save(en);
            return ResponseEntity.ok().build();
        }
        if(id == null)
        {
          return ResponseEntity.internalServerError().build();
        }
        if(!id.equals(en.getId()))
        {
           return ResponseEntity.internalServerError().build();
        }
        return null;

    }

    public ResponseEntity<?> deleteorder (Long id)
    {
        if(id == null)
        {
            return ResponseEntity.internalServerError().build();

        }
        else
        {
            Repo.deleteById(id);
            return ResponseEntity.ok("DELETED");
        }

    }

    public ResponseEntity<?> cancelorder( Long id)
    {
        if(id == null )
        {

            return ResponseEntity.badRequest().body("You must Enter Valid ID");
        }
        Optional<entity> ordernumber = Repo.findById(id);
       if(ordernumber.isEmpty())
       {
            return ResponseEntity.notFound().build();
       }
        entity en=ordernumber.get();
        en.setOrder_status("Canceled");
        Repo.save(en);
        return ResponseEntity.ok().build();
    }
    public ResponseEntity<String> forcedorder( Long id)
    {
        if(id == null )
        {
            return ResponseEntity.notFound().build();
        }
        Optional<entity> ordernumber = Repo.findById(id);
        if(!ordernumber.isPresent())
        {
            return ResponseEntity.internalServerError().build();
        }
        entity en=ordernumber.get();
        en.setOrder_status("Forced");
        Repo.save(en);
        return ResponseEntity.ok().build();
    }

}
