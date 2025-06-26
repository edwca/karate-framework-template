const fs = require('fs');
const crypto = require('crypto');
const path = require('path');

const secret = process.env.ENV_SECRET_KEY;
if (!secret) throw new Error('❌ ENV_SECRET_KEY no definida');

const key = crypto.createHash('sha256').update(secret).digest();
const algorithm = 'aes-256-cbc';

const decrypt = (buffer) => {
  const iv = buffer.slice(0, 16);
  const content = buffer.slice(16);
  const decipher = crypto.createDecipheriv(algorithm, key, iv);
  return Buffer.concat([decipher.update(content), decipher.final()]);
};

const files = ['env.qa.json.enc', 'env.devel.json.enc', 'env.pre-prod.json.enc'];

files.forEach(file => {
  const inputPath = path.join('.', file);
  const outputPath = inputPath.replace('.enc', '');
  const data = fs.readFileSync(inputPath);
  const decrypted = decrypt(data);
  fs.writeFileSync(outputPath, decrypted);
  console.info(`🔓 ${path.basename(outputPath)} descifrado`);
});
