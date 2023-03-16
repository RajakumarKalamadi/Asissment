import java.io.FileWriter;
import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ProductScraper {
   public static void main(String[] args) {
      // URL to scrape
      String url = "https://www.staples.com/Computer-office-desks/cat_CL210795/663ea?icid=BTS:2020:STUDYSPACE:DESKS";

      try {
         // Connect to the URL and retrieve the HTML document
         Document doc = Jsoup.connect(url).get();

         // Select all the product containers
         Elements products = doc.select("div.sku_wrapper");

         // Create a new CSV file and write the header
         FileWriter csvWriter = new FileWriter("product_details.csv");
         csvWriter.append("Product Name,Product Price,Item Number/SKU/Product Code,Model Number,Product Category,Product Description\n");

         // Loop through each product container and extract the product details
         for (Element product : products) {
            String name = product.select("a.sku_link").text();
            String price = product.select("div.current_price").text();
            String itemNumber = product.select("div.sku_info").text();
            String modelNumber = product.select("div.sku_info").text();
            String category = product.select("div.breadcrumb").text();
            String description = product.select("div.short_description").text();

            // Write the product details to the CSV file
            csvWriter.append(name + "," + price + "," + itemNumber + "," + modelNumber + "," + category + "," + description + "\n");
         }

         // Close the CSV file
         csvWriter.flush();
         csvWriter.close();

         System.out.println("Product details successfully saved to product_details.csv");
      } catch (IOException e) {
         e.printStackTrace();
      }
   }
}
