import java.io.BufferedReader;
import java.io.InputStreamReader;

public class JenkinsServiceChecker {
    
    public static void main(String[] args) {
        checkJenkinsService();
    }
    
    public static void checkJenkinsService() {
        try {
            Process process = Runtime.getRuntime().exec("systemctl is-active jenkins");
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String status = reader.readLine();
            
            if ("active".equals(status)) {
                System.out.println("✓ Jenkins service is running");
            } else {
                System.out.println("✗ Jenkins service is not running. Status: " + status);
            }
            
        } catch (Exception e) {
            System.out.println("Error checking Jenkins service: " + e.getMessage());
            
            // Fallback: try service command
            try {
                Process process = Runtime.getRuntime().exec("service jenkins status");
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.contains("active (running)")) {
                        System.out.println("✓ Jenkins service is running");
                        return;
                    }
                }
                System.out.println("✗ Jenkins service is not running");
            } catch (Exception ex) {
                System.out.println("Cannot determine Jenkins service status");
            }
        }
    }
}
