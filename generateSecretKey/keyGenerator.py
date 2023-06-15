import secrets
import base64

# Generate a 512-bit key
key = secrets.token_bytes(64)  # 64 bytes * 8 = 512 bits

# Get key as a base64 string to use in your app
encoded_key = base64.b64encode(key).decode()

# Print the key
print(encoded_key)
