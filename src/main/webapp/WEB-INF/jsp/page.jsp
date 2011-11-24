<%@ page pageEncoding="UTF-8"%>
<%@page import="com.eleven7.imall.dao.base.PageBean;"%>
<table>
<%
	PageBean pages = (PageBean) request.getAttribute("page");
	if(pages !=null && pages.getTotal() > -1)
    {
          out.println("<tr>");
         
      
         if(pages.isHasPre())
         {
        	 out.println("<td>");
        	 out.println("<a href=\"javascript:void(0);\" onclick=\"jumpPage('1');return false;\">首页</a>"); 
        	 out.println("</td>");
        	 
        	 out.println("<td>");
        	 out.println("<a href=\"javascript:void(0);\" onclick=\"jumpPage('"+(pages.getPage()-1)+"');return false;\">上一页</a>"); 
        	 out.println("</td>");
        	 
         }         
         else 
         {
        	 out.println("<td>");
        	 out.println("首页");
        	 out.println("</td>");
        	 out.println("<td>");
        	 out.println("上一页");
        	 out.println("</td>");
         }
                int total = pages.getTotalPages();
                int currentPage = pages.getPage();
                int left = currentPage -1;
                int right =total -currentPage;
               
                if(left >=2 && right >=2)
                {
                  left =2;
                  right =2;
                }
                else if(left <2 && right >=2)
                { 
                  int temp =5-(left +1);
                  if(right > temp )
                     right =temp;
                }
                else if(left >=2 && right <2)
                {
                  int temp = 5-(right+1);
                  if(left > temp)
                    left =temp;
                }
                
                
				for(int i = left ;i > 0;i--)
				{
                   out.println("<td>");
                   int tmpPage = currentPage-i;

                    out.println(" <a href=\"javascript:void(0);\" onclick=\"jumpPage('"+tmpPage+"');return false;\">[ "+tmpPage+" ]</a>");
                    out.println("</td>");
				}
				   out.println("<td>[ "+pages.getPage()+" ]</td>");                 

                for(int i = 1;i <= right;i++)
				{	
			          out.println("<td>");
				
				      out.println("<a href=\"javascript:void(0);\" onclick=\"jumpPage('"+(currentPage+i)+"');return false;\">[ "+(currentPage+i)+" ]</a>");
							
				      out.println("</td>");
			
				}

		
      
         if(pages.isHasNext())
         {
        	 out.println("<td>");
        	 out.println("<a href=\"javascript:void(0);\" onclick=\"jumpPage('"+(pages.getPage()+1)+"');return false;\">下一页</a>");
        	 out.println("</td>");
        	 
        	 out.println("<td>");
        	 out.println("<a href=\"javascript:void(0);\" onclick=\"jumpPage('"+(pages.getTotalPages())+"');return false;\">末页</a>");
        	 out.println("</td>");
         }
          
         else 
         {
        	 out.println("<td>");
        	 out.println("下一页");
        	 out.println("</td>");
        	 out.println("<td>");
        	 out.println("末页");
        	 out.println("</td>");
        	 	 
         }
         out.println("<td> 共 "+pages.getTotalPages()+" 页, "+pages.getTotal()+" 条记录</td>");
         
        out.println("</tr>");
    }
%>
</table>
